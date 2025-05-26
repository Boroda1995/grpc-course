import grpc
import pingpong_pb2
import pingpong_pb2_grpc

def run():
    print("Подключаемся к серверу...")

    with grpc.insecure_channel('localhost:8080') as channel:
        stub = pingpong_pb2_grpc.PingPongServiceStub(channel)

        print("Отправляем запрос...")
        request = pingpong_pb2.PingRequest(message="Привет от Python!")

        response = stub.Ping(request)

        print(f"Получен ответ от сервера: {response.message}")

if __name__ == '__main__':
    run()



