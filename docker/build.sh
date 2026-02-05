image_name="luaimageservice"

docker build . --file Dockerfile --tag $image_name
docker save $image_name -o image.tar
