FROM python:latest

WORKDIR /app

COPY . /app

RUN pip install flask werkzeug

RUN mkdir -p uploads

EXPOSE 5000

CMD ["python", "main.py"]
