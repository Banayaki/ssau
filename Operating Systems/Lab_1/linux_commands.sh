#!/usr/bin/env bash

echo Now, we will be moved in lab_1 directory
cd PreFire/ssau/Operating\ Systems/Lab_1/

echo Now, we will create new folders here
mkdir -p SomeDirectory/folder{1..7}
cd ./SomeDirectory
ls -l

echo Now, we will create new files here
touch file{1..7}.txt
ls -l

echo Now, we will move all files in folder1
mv -v *.txt folder1/

echo Enter the number a:
read a
echo Enter the number b:
read b
let "c = a + b"
echo "a + b = $c"

rm ~/PreFire/ssau/Operating\ Systems/Lab_1/SomeDirectory -rf