from cmath import atan, tan, cos, sin

import cv2
import numpy as np


def f(_focal_length, _k, _theta):
    if _k > 0:
        return _focal_length / _k * tan(_k * _theta)
    elif _k < 0:
        return _focal_length / _k * sin(_k * _theta)
    else:
        return _focal_length * _theta
    pass


if __name__ == '__main__':
    img = cv2.imread('/home/banayaki/Downloads/2.png')
    img = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    cv2.imshow('f', img)
    # cv2.waitKey(0)
    focal_length = img.shape[0] // 3
    k = 1

    cy, cx = np.array(img.shape[:2]) // 2
    dist_image = np.zeros(np.array(img.shape) + 1000, np.uint8)
    print(img[0][0])

    for source_x in range(img.shape[1]):
        for source_y in range(img.shape[0]):
            xpos = source_x > cx
            ypos = source_y > cy
            xdif = source_x - cx
            ydif = source_y - cy
            source_radius = (xdif * xdif + ydif * ydif) ** 0.5
            angle = atan(ydif/xdif).real
            angle = np.rad2deg(angle)
            if not xpos:
                angle += 180
            angle = np.deg2rad(angle)
            theta = source_radius / focal_length
            dist_radius = f(focal_length, k, theta)
            if np.isnan(theta):
                theta = 90

            dist_x = dist_radius * cos(angle) + cx
            dist_y = dist_radius * sin(angle) + cy
            if np.isnan(dist_x) or np.isnan(dist_y):
                print(f'{source_radius} {source_x} {source_y}')
                continue
            if 0 < int(round(dist_y)) < img.shape[0] and 0 < int(round(dist_x)) < img.shape[1]:
                dist_image[int(round(dist_y))][int(round(dist_x))] = img[source_y][source_x]
    print(dist_image)
    cv2.imshow('f', dist_image)
    cv2.waitKey(0)
    cv2.destroyAllWindows()
