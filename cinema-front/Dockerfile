FROM node:20-alpine3.16 AS build
WORKDIR /app
# Kopiranje package.json i package-lock.json
COPY package*.json ./
RUN npm install

# Kopiranje svih fajlova
COPY . .
# Build aplikacije
RUN npm run build

# Postavljanje Nginx-a za serviranje statičkog sadržaja
FROM nginx:alpine
COPY --from=0 /app/dist /usr/share/nginx/html

# 6. Kopiraj custom nginx config ako treba (opciono)
# COPY nginx.conf /etc/nginx/nginx.conf

# Expose port 80 za HTTP
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]

#hotel-fronted je naziv kontejnera
