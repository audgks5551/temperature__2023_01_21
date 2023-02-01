국번 1
통신속도 9600
스탑비트 1
바이너리 8

docker buildx build \
--push \
--platform linux/arm64/v8,linux/amd64 \
--tag audgks5551/temperature:2.0 .

docker run -d -p 8080:8080 -v /sys/bus/w1:/sys/bus/w1 --name=temperature audgks5551/temperature:2.0

vim /boot/firmware/config.txt
dtoverlay=w1-gpio,gpiopin=23