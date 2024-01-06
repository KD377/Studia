import requests
BASE_URL = 'http://127.0.0.1:8000/zad3/api/data/'

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
    'continuous_feature1': 1.111,
    'continuous_feature2': 2.222,
    'categorical_feature': 3
}

response = requests.post(BASE_URL, data=new_data)
if response.status_code == 201:
    data = response.json()
    print("POST /api/data - Success")
    print("New data point ID:", data['id'])
else:
    print("POST /api/data - Failed")


# DELETE
record_id = 7

response = requests.delete(f'{BASE_URL}{(record_id)}')
if response.status_code == 200:
    print("DELETE /api/data - Success")
    data = response.json()
    print("Deleted record ID:", data.get('deleted_record_id'))
elif response.status_code == 404:
    print("DELETE /api/data - Failed")
    print(response.status_code)  
    print(response.json())
else:
    print("DELETE /api/data - Failed")