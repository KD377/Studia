from django.shortcuts import render, redirect
from django.http import HttpResponseNotFound, HttpResponseBadRequest, JsonResponse, HttpResponseNotAllowed
from .models import Table1, Session
from .forms import SQLAlchemyForm
from django.views.decorators.csrf import csrf_exempt

def display_all_data(request):
    session = Session()
    all_data =session.query(Table1).all()
    return render(request, 'home.html', {'data' : all_data})

def delete_record(request, record_id):
    if request.method == 'POST':
        session = Session()
        record = session.query(Table1).get(record_id)
        if record:
            session.delete(record)
            session.commit()
            return redirect('/')
        else:
            return HttpResponseNotFound('<h1>404 - Not found</h1>')
    else:
        return HttpResponseNotAllowed('<h1>405 - Method not allowed</h>')


def add_data(request):
    if request.method == "POST":
        form = SQLAlchemyForm(request.POST)
        if form.validate():
            continuous_feature1 = form.continuous_feature1.data
            continuous_feature2 = form.continuous_feature2.data
            categorical_feature = form.categorical_feature.data
            
            session = Session()
            new_record = Table1(
                continuous_feature1=continuous_feature1,
                continuous_feature2=continuous_feature2,
                categorical_feature=categorical_feature
            )
            session.add(new_record)
            session.commit()
            return redirect('/')
        else:
            return HttpResponseBadRequest('<h1>400 - Bad request<h1>')
    else:
        form = SQLAlchemyForm()
        return render(request, 'add_data.html', {'form': form})

@csrf_exempt
def data_api(request):
    if request.method == 'GET':
        session = Session()
        data = session.query(Table1).all()
        session.close()

        data_list = []
        for item in data:
            data_list.append({
                'id': item.id,
                'continuous_feature1': item.continuous_feature1,
                'continuous_feature2': item.continuous_feature2,
                'categorical_feature': item.categorical_feature
            })

        return JsonResponse(data_list, safe=False)
    elif request.method == 'POST':
        try:
            data = request.POST  

            
            continuous_feature1 = data.get('continuous_feature1')
            continuous_feature2 = data.get('continuous_feature2')
            categorical_feature = data.get('categorical_feature')

            
            if(
            continuous_feature1 is None
            or continuous_feature2 is None
            or categorical_feature is None
            or not continuous_feature1.replace('.', '', 1).isdigit()
            or not continuous_feature2.replace('.', '', 1).isdigit()
            or not categorical_feature.replace('-', '', 1).isdigit()
            or '.' in categorical_feature
            ):
                raise ValueError("Invalid data")
        

            new_record = Table1(
                continuous_feature1=continuous_feature1,
                continuous_feature2=continuous_feature2,
                categorical_feature=categorical_feature
            )
            session = Session()
            session.add(new_record)
            session.commit() 

            return JsonResponse({'id': new_record.id}, status=201)

        except ValueError as e:
            return JsonResponse({'error': str(e)}, status=400)
    return JsonResponse({'error': 'Method not allowed'}, status=405)

@csrf_exempt
def delete_api(request, record_id):
    if request.method == "DELETE":
        session = Session()
        record = session.query(Table1).get(record_id)
        if record:
            session.delete(record)
            session.commit()
            return JsonResponse({'deleted_record_id': record_id}, status=200)
        else:
            return JsonResponse({'error': 'Record not found'}, status=404)
    else:
        return JsonResponse({'error': 'Method not allowed'}, status=405)

