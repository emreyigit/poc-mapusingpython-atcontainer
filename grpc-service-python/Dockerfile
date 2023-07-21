FROM python:3.11

WORKDIR /var/task

COPY main.py main.py
COPY jet_to_python_pb2.py jet_to_python_pb2.py
COPY jet_to_python_pb2_grpc.py jet_to_python_pb2_grpc.py
COPY requirements.txt requirements.txt

ENV LANG=en_US.UTF-8
ENV TZ=:/etc/localtime
ENV PATH=/var/lang/bin:/usr/local/bin:/usr/bin/:/bin:/opt/bin
ENV LD_LIBRARY_PATH=/var/lang/lib:/lib64:/usr/lib64:/var/runtime:/var/runtime/lib:/var/task:/var/task/lib:/opt/lib
ENV USER_CODE_TASK_ROOT=/var/task
ENV USER_CODE_RUNTIME_DIR=/var/runtime

RUN python3 -m pip install -r requirements.txt

ENTRYPOINT ["python3","main.py"]