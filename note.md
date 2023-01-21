국번 1
통신속도 19200
스탑비트 1
바이너리 8

docker buildx build \
--push \
--platform linux/arm64/v8,linux/amd64 \
--tag audgks5551/temperature:0.2 .