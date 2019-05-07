# -*- coding: utf-8 -*-
"""
Created on Mon Apr 29 18:34:38 2019

@author: guill
"""

print("debutdupython")
#import time
print("importcoupe1")

#from cv2 import *
print("importcoupe2")

import time
#import cv2
#
#import numpy
#from fastai.vision import *
#from fastai import *
import sys
from pathlib import Path
from torchvision import transforms
from PIL import Image
import fastai
from fastai.vision import *
from fastai import *

print("importcoupe4")

#import fastai
print("importcoupe3")
#fichiertest = open(r"..\temporaryFiles\test.txt", "w")
#fichiertest.write("je teste un truc la")
#fichiertest.close



print("testpywtf")
#fichier2 = open(r"C:\Users\alexa\Desktop\LEBONGIT\twizy_project_2019\TemporaryFiles\whenAreWe.txt", "r")
#iApp = fichier2.read()
#fichier2.close()  
iApp = int( sys.argv[1] )

#fichier3 = open(r"C:\Users\alexa\Desktop\LEBONGIT\twizy_project_2019\TemporaryFiles\howMany"+str(iApp)+".txt", "r")
#N = int(fichier3.read())
#fichier3.close()
N = int(sys.argv[2])
print(str(N)+"|"+str(iApp))






#N=5
#iApp=6
result =[]


for i in range(1,N+1):
    

    img = open_image(r"C:\Users\alexa\Desktop\LEBONGIT\twizy_project_2019\temporaryFiles\tempIm"+str(iApp)+"s"+str(i)+".png")
    learn = load_learner(path =r"C:\Users\alexa\Desktop\LEBONGIT\twizy_project_2019\Pytorch Folder")
    pred_class,pred_idx,outputs = learn.predict(img)
    print("pred"+str(iApp)+"|"+str(pred_class))
    result.append(str(pred_class))
    
fichier = open(r"C:\Users\alexa\Desktop\LEBONGIT\twizy_project_2019\temporaryFiles\result"+str(iApp)+".txt", "w")
print("result"+str(iApp))
for r in result:
    fichier.write(str(r)+"\n")


fichier.close()

