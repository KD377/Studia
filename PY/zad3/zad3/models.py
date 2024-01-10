from sqlalchemy import create_engine, Column, Integer, Float
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker

engine = create_engine('sqlite:///db.sqlite3')
Session = sessionmaker(bind=engine)

Base = declarative_base()

class Table1(Base):
    __tablename__ = 'table1'

    id = Column(Integer, primary_key=True)
    continuous_feature1 = Column(Float)
    continuous_feature2 = Column(Float)
    categorical_feature = Column(Integer)
