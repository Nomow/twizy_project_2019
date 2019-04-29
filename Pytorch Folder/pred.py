# -*- coding: utf-8 -*-
"""
Created on Mon Apr 29 18:34:38 2019

@author: guill
"""

import time
import cv2

import numpy
from fastai.vision import *
from fastai import *
from pathlib import Path
from torchvision import transforms
from PIL import Image
import fastai


defaults.device = torch.device('cpu')
img = open_image(r"C:\Users\guill\Downloads\dataDL\inferences2\test6.jpg")
learn = load_learner(path =r"C:\Users\guill\Downloads\dataDL")
pred_class,pred_idx,outputs = learn.predict(img)
print(pred_class)

