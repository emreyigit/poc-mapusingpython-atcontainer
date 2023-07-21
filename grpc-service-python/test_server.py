import grpc

import jet_to_python_pb2
import jet_to_python_pb2_grpc
from concurrent import futures

class JetToPythonServer(jet_to_python_pb2_grpc.JetToPythonServicer):

    def streamingCall(self, request, context):
        for item in request:
            print(item.inputValue)
            outputValue = [m.upper() for m in item.inputValue]
            yield jet_to_python_pb2.OutputMessage(outputValue=outputValue)

def main():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=1))
    server.add_insecure_port('[::]:50051')
    jet_to_python_pb2_grpc.add_JetToPythonServicer_to_server(
        JetToPythonServer(),
        server
    )
    server.start()
    print('Server started at localhost:50051')
    server.wait_for_termination()

if __name__ == '__main__':
    main()
