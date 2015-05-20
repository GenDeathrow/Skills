########################################
# Updates Version numbers and builds ForgeGradle
########################################

#Get build type
buildtype=$(grep -oP "<build>(.*)</build>" config.xml | cut -d '>' -f 2 | cut -d '<' -f 1)

mkdir -p ./wiki
cd ./wiki

# Clone wiki https://github.com/GenDeathrow/Skills.wiki.git
git clone git@github.com:GenDeathrow/Skills.wiki.git ./

#Get Current Version from Github
curversion=$(grep -oP "<${build_text}>(.*)</${build_text}>" Version_Info.md | cut -d '>' -f 2 | cut -d '<' -f 1)

cd ../

# Replace with update version in config.xml
grep -lR -e "<version>.*<\/version>" config.xml | xargs sed -i "s/<version>.*<\/version>/<version>${curversion}<\/version>/g"

# Increment Version Number 
eval ./gradlew -q ${buildtype}

#Get new Version number
newversion=$(grep -oP "<version>(.*)</version>" config.xml | cut -d '>' -f 2 | cut -d '<' -f 1)

#Replace all ver keys with new Number
grep -lRr -e $ver_key * | xargs sed -i "s/$ver_key/$newversion/g"

#Build Forge
./gradlew clean setupCIWorkSpace build


#Move back to wiki to update
cd ./wiki

# Replace with update version in  wiki
grep -lR -e "<${build_text}>.*<\/${build_text}>" *| xargs sed -i "s/<${build_text}>.*<\/${build_text}>/<${build_text}>${newversion}<\/${build_text}>/g"


#Replace old build with new one
#grep -lR -e "<${build_text}>.*</${build_text}>" * | xargs sed -i "s/<${build_text}>.*<\/${build_text}>/<${build_text}>$ver_num<\/${build_text}>/g"

#Replace old date with new one
#grep -lR -e "<${date_text}>.*</${date_text}>" * | xargs sed -i "s/<${date_text}>.*<\/${date_text}>/<${date_text}>${cur_date}<\/${date_text}>/g"

#Replace old download link with new one
#grep -lR -e "\[downloads\-1\.7\]:.*" * | xargs sed -i "s/\[downloads\-1\.7\]:.*/\[downloads\-1\.7\]: ${download_link}/g"

#Replace old build link with new one
#grep -lR -e "\[build\-1\.7\]:.*" * | xargs sed -i "s/\[build\-1\.7\]:.*/\[build\-1\.7\]: ${build_link}/g"


#Commit the new page
git add .
git commit -m "Updated version"
git push -u origin master



