from django.shortcuts import render, redirect, get_object_or_404
from django.http import HttpResponseNotFound, HttpResponseBadRequest
from .models import Table1
from .forms import Form

def display_all_data(request):
    all_data = Table1.objects.all()
    return render(request, 'home.html', {'data' : all_data})

def delete_record(request, record_id):
    if request.method == 'POST':
        record = get_object_or_404(Table1, pk=record_id)
        record.delete()
        return redirect('/zad3')
    else:
        return HttpResponseNotFound('<h1>404 - Not found</h1>')


def add_data(request):
    if request.method == "POST":
        form = Form(request.POST)
        if form.is_valid():
            form.save()
            return redirect('/zad3')
        else:
            return HttpResponseBadRequest('<h1>400 - Bad request</h1>')
    else:
        form = Form()
        return render(request, 'add_data.html', {'form': form})