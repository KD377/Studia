from django import forms
from .models import Table1

class Form(forms.ModelForm):
    class Meta:
        model = Table1
        fields = ['continuous_feature1', 'continuous_feature2', 'categorical_feature']