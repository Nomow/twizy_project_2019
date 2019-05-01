# -*- coding: utf-8 -*-
"""
Created on Sun Apr 28 16:50:15 2019

@author: guill
"""

#How to infer using Python.

import requests
import base64
import json


API_LINK = "https://api.panini.ai/ftly4iqc4tpg1skzwhklu3m2zz03-cnntwi/predict" #Your API URL goes here


img = r"C:\Users\guill\Downloads\dataDL\inferences2\test9.jpg"

with open(r"C:\Users\guill\Downloads\dataDL\inferences\test10.jpg", "rb") as imageFile:
    str = base64.b64encode(imageFile.read())
    str = str.decode("utf-8")
    print (str)

  #Data you want to send for prediction goes here

data = [str]

#print(json.dumps(data))

response = requests.post(
     API_LINK,
     headers={"Content-type": "application/json"},
     data=json.dumps({
         'input': str,
     }))
result = response.json()
print(result) #Prediction response