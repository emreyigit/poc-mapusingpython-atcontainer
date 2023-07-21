FROM emreyigit/grpc-python-base:latest

# Copy requirements.txt
# COPY requirements.txt ${USER_CODE_TASK_ROOT}

# Copy function code
COPY transform.py ${USER_CODE_TASK_ROOT}/transform.py

# Install the specified packages
# RUN pip install -r requirements.txt

# Set the CMD to your handler (could also be done as a parameter override outside of the Dockerfile)
CMD [ "8091","transform","transform_list" ]