##########################################
##### FRONTEND BUILD
##########################################

FROM node:16.9.1-alpine AS build

WORKDIR /app

COPY ["package.json", "package-lock.json*", "./"]

RUN npm install

COPY . .

RUN npm run build

##########################################
##### FRONTEND FINAL
##########################################

FROM node:16.9.1-alpine

WORKDIR /app

RUN npm install -g webserver.local

COPY --from=build /app/build ./build

EXPOSE 3000

CMD webserver.local -d ./build