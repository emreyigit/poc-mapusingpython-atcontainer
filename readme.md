# POC of  `mapUsingPython` Containerization
The poc aims to run python grpc runtime service in conianter to seperate user code from Hazelcast instance.

Steps to run POC

###  1. Build the base Docker image which contains the Python grpc service.

Build the docker image at `./grpc-service-python`

```
docker build -t emreyigit/grpc-python-base:latest .
```

### 2. Build the user code Docker image from Python grpc service image.
   
   Prepare the transform function at `./grpc-service-python-user-code`, and build the Docker image. `transform.py` file is already contains a transformation function, and it will be copied to container with user function name.

   Build the docker image at `./grpc-service-python-user-code`

```
docker build -t emreyigit/user-code:latest .
```

### 3. Compile the `./hazelcast/extensions/python` extension.
Compile the extension at `./hazelcast/extensions/python`.

```
mvn clean install -DskipTests
```
Then, `hazelcast-jet-python-5.4.0-SNAPSHOT-jar-with-dependencies.jar` should be copied to `lib` directory Hazelcast runtime(Don't forget to remove old jet python extension in the directory). Then start Hazelcast. 

### 4. Prepare a JetJob

Compile the Jet job at `./java-jet-job`.
```
mvn clean install -DskipTests
```

### 5. Run user code worker controller
The controller is the middleware between Jet and Docker/K8s env. To run a container that contains the user code for each job, it is required.

```
docker run --rm -it -v /var/run/docker.sock:/var/run/docker.sock  -p 8080:8080 -u0 dzeromskhazelcast/user-code-sidecar:latest
```
Note: keep the terminal open to see logs.

### 6. Submit the job

Everything should be up until now. Submit the Jet job `./java-jet-job/target/tutorial-python-1.0-SNAPSHOT.jar` to Hazelcast cluster.

```
hz-cli submit PATH_TO_JAR_FILE/tutorial-python-1.0-SNAPSHOT.jar
```

Note: keep the terminal open to see logs.

After job is submit, `mapUsingPython` function will ask worker controller to run a `emreyigit/user-code` image. Then, will connecto its grpc 8090 port and pass the data to user function. You can also see the worker container by `docker ps` command.