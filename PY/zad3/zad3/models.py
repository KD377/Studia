from django.db import models

# # Create your models here.
class Table1(models.Model):
    # Kolumna dla klucza głównego
    primary_key = models.AutoField(primary_key=True)
    # Kolumny dla cech ciągłych
    continuous_feature1 = models.FloatField()
    continuous_feature2 = models.FloatField()
    # Kolumna dla cechy kategorycznej
    categorical_feature = models.IntegerField()

