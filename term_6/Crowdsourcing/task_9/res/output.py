import math
import os


def get_distance(lat_1, lng_1, lat_2, lng_2):
    d_lat = lat_2 - lat_1
    d_lng = lng_2 - lng_1

    temp = (
         math.sin(d_lat / 2) ** 2
       + math.cos(lat_1)
       * math.cos(lat_2)
       * math.sin(d_lng / 2) ** 2
    )

    return 6373.0 * (2 * math.atan2(math.sqrt(temp), math.sqrt(1 - temp)))


def convert_to_radians(str):
    val = float(str)
    rad = math.radians(val)
    return rad


def parse(coordinates):
    lat, lng = coordinates.split(',')
    return convert_to_radians(lat), convert_to_radians(lng)


def extract_image_coordinates(image_path):
    filename = image_path
    im = Image.open(filename)

    exif_dict = piexif.load(im.info.get('exif'))
    pprint(exif_dict['GPS'])


inFileName = "current_submissions.tsv"
outFileName = "rejected.tsv"

with open(inFileName, "r") as f:
    txt = f.read()


lines = txt.split('\n')
res = [lines[0]]
for line in txt.split('\n')[1:]:
    vals = line.split('\t')
    golden_lat, golden_lng = parse(vals[1]) # INPUT:position
    actual_lat, actual_lng = parse(vals[18]) # prefetched coordinates from one of the images (extract_image_coordinates())
    dist_in_km = get_distance(golden_lat, golden_lng, actual_lat, actual_lng)
    dist_in_m = 1e3 * dist_in_km
    if dist_in_m > 1000:
        print(dist_in_m)
        res.append('\t'.join(vals[:12]) + "\tREJECTED\t" + '\t'.join(vals[13:16]) + '\t\tКоординаты фотографий указывают на то, что задание было сдлеано нечестно.')


if len(res) == 1:
    print("*** All submissions are fine ;) ***")
else:
    print("*** Found %d submissions with incorrect coordinates in image :( ***"%(len(res)-1))


outputAlreadyExists = os.path.isfile(outFileName)
flag = "w" if outputAlreadyExists else "x"
with open(outFileName, flag) as o:
    o.write('\n'.join(res))


def extract_image_coordinates(image_path):
    filename = image_path
    im = PIL.Image.open(filename)

    exif_dict = piexif.load(im.info.get('exif'))
    raw_coordinates = exif_dict['GPS']
    coordinates = butify(raw_coordinates)
    print(exif_dict['GPS'])