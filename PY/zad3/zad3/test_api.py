import requests
BASE_URL = 'http://127.0.0.1:8000/api/data/'

# GET
response = requests.get(BASE_URL)
if response.status_code == 200:
    data = response.json()
    print("GET /api/data - Success")
    print("Data points:", data)
else:
    print("GET /api/data - Failed")

# POST
new_data = {
    'continuous_feature1': 1.11,
    'continuous_feature2': 2.22,
    'categorical_feature': 33
}

response = requests.post(BASE_URL, data=new_data)
if response.status_code == 201:
    print("POST /api/data - Success")
    print(response.status_code)
    print(response.json())
else:
    print("POST /api/data - Failed")
    print(response.status_code)
    print(response.json())


# DELETE
record_id = 2

response = requests.delete(f'{BASE_URL}{(record_id)}')
if response.status_code == 200:
    print("DELETE /api/data - Success")
    print(response.status_code)
    print(response.json())
else:
    print("DELETE /api/data - Failed")
    print(response.status_code)  
    print(response.json())