from wtforms_alchemy import model_form_factory
from .models import Table1 
from wtforms import Form, FloatField, IntegerField

BaseModelForm = model_form_factory(Form)

class SQLAlchemyForm(BaseModelForm):
    continuous_feature1 = FloatField('Continuous Feature 1')
    continuous_feature2 = FloatField('Continuous Feature 2')
    categorical_feature = IntegerField('Categorical Feature')

    class Meta:
        model = Table1