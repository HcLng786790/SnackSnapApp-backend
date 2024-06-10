# Sử dụng hình ảnh OpenJDK để chạy ứng dụng
FROM openjdk:17-jdk-alpine

# Thiết lập thư mục làm việc trong container
RUN mkdir /snacksnap
WORKDIR /snacksnap

# Sao chép tất cả các file JAR từ thư mục target vào container
COPY target/snacksnap-0.0.1-SNAPSHOT.jar snacksnap.jar

# Mở cổng 8080 để ứng dụng có thể nhận các kết nối
EXPOSE 8080

# Định nghĩa lệnh để chạy ứng dụng khi container khởi động
ENTRYPOINT ["java", "-jar", "snacksnap.jar"]

