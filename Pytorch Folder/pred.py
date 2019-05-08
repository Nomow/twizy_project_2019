# -*- coding: utf-8 -*-
"""
Created on Mon Apr 29 18:34:38 2019

@author: guill
"""

print("debutdupython")

import sys
from pathlib import Path
from torchvision import transforms
from PIL import Image
import fastai
from fastai.vision import *
from fastai import *


iApp = int( sys.argv[1] )

N = int(sys.argv[2])
print(str(N)+"|"+str(iApp))


classes = pd.read_csv(r"C:\Users\alexa\Desktop\LEBONGIT\twizy_project_2019\\Pytorch Folder\signnames.csv")
class_names = {}
for i, row in classes.iterrows():
    class_names[str(row[0])] = row[1]



#N=5
#iApp=6
result =[]
learn = load_learner(path =r"C:\Users\alexa\Desktop\LEBONGIT\twizy_project_2019\Pytorch Folder")


class_indexes = []
for i in range(len(learn.data.classes)):
    class_indexes.append([i,learn.data.classes[i]])
    print (class_indexes[i][0])

y_pred = []
for i in range(1,N+1):
    

    img = open_image(r"C:\Users\alexa\Desktop\LEBONGIT\twizy_project_2019\temporaryFiles\tempIm"+str(iApp)+"s"+str(i)+".png")
    pred_class,pred_idx,outputs = learn.predict(img)
    print("pred"+str(iApp)+"|"+str(pred_class))
    
    #result.append(str(pred_class))
    


  
    for index in range (len(class_indexes)):
        if(int(pred_class) == class_indexes[index][1]):
            index = class_indexes[index][0];
            probaMax = float(outputs[index])
            if (probaMax>=0.8):
                print("proba"+str(probaMax))
                y_pred.append(pred_class)
        
    
fichier = open(r"C:\Users\alexa\Desktop\LEBONGIT\twizy_project_2019\temporaryFiles\result"+str(iApp)+".txt", "w")
print("result"+str(iApp))
for y in y_pred:
    fichier.write(str(y)+"\n")


fichier.close()

