import os

import click
import cv2
import numpy as np


def img_radius(_focal_length, _k, _theta):
    if _k > 0:
        return _focal_length / _k * np.tan(_k * _theta)
    elif _k < 0:
        return _focal_length / _k * np.sin(_k * _theta)
    else:
        return _focal_length * _theta
    pass


def transform(image, _k, _focal_length):
    img_shape = image.shape[:2]
    source_map = np.indices(img_shape, dtype=np.int32)
    cy, cx = np.array(img_shape) // 2
    center = np.array([[[cy]], [[cx]]])
    source_map = source_map - center
    source_radius = np.sqrt(source_map[0] * source_map[0] + source_map[1] * source_map[1])
    angle = np.arctan2(source_map[0], source_map[1])

    theta = np.arctan2(source_radius, _focal_length)
    dist_radius = img_radius(_focal_length, _k, theta)

    map_x = dist_radius * np.cos(angle) + cx
    map_y = dist_radius * np.sin(angle) + cy
    dist_img = cv2.remap(image, map_x.astype(np.float32), map_y.astype(np.float32), cv2.INTER_CUBIC)
    return dist_img


@click.command()
@click.option('--input-path', '-i')
@click.option('--focal', '-f')
@click.option('--distort', '-k')
@click.option('--output-path', '-o')
def main(input_path, focal, distort, output_path):
    assert (input_path is not None)
    assert (focal is not None)
    assert (distort is not None)
    assert (output_path is not None)
    assert (len(output_path) > 0)
    assert (os.path.exists(input_path))
    k = float(distort)
    focal_length = float(focal)
    img = cv2.imread(input_path)
    dist_img = transform(img, k, focal_length)
    cv2.imwrite(output_path, dist_img)
    pass


if __name__ == '__main__':
    main()
