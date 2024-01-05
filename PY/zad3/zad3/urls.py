from django.urls import path
from . import views


urlpatterns = [
    path('', views.display_all_data, name='home'),
    path('delete/<int:record_id>/', views.delete_record, name='delete_record'),
    
]