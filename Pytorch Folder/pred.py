# -*- coding: utf-8 -*-
"""
Created on Mon Apr 29 18:34:38 2019

@author: guill
"""
print("testpy0")
import time
import cv2

import numpy
from fastai.vision import *
from fastai import *
from pathlib import Path
from torchvision import transforms
from PIL import Image
import fastai

import argparse
print("testpy")
 
parser = argparse.ArgumentParser(description='Process some integers.')
parser.add_argument('integers', metavar='N', type=int, nargs='+',
help='an integer for the accumulator')
 
args = parser.parse_args()



defaults.device = torch.device('cuda')
img = open_image(r"C:\Users\alexa\Desktop\twizy_project_2019\temporaryFiles\tempImTe"+args+".png")
learn = load_learner(path =r"C:\Users\alexa\Desktop\twizy_project_2019\Pytorch Folder")
pred_class,pred_idx,outputs = learn.predict(img)
print(pred_class)

