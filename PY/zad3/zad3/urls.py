from django.urls import path
from . import views


urlpatterns = [
    path('', views.display_all_data, name='home'),
    path('delete/<int:record_id>/', views.delete_record, name='delete_record'),
    path('add/', views.add_data, name='add_data'),
    path('api/data/', views.data_api, name='data_api'),
    path('api/data/<int:record_id>/', views.delete_api, name='delete_api'),
    
]