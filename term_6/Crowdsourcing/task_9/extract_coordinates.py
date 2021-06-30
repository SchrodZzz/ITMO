import PIL
import piexif

def extract_image_coordinates(image_path):
    filename = image_path
    im = PIL.Image.open(filename)

    exif_dict = piexif.load(im.info.get('exif'))
    print(exif_dict['GPS'])


extract_image_coordinates('test.jpg')