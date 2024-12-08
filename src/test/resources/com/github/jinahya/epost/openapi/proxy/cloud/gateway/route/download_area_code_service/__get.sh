#!/bin/sh
# https://www.epost.go.kr/search/zipcode/areacdAddressDown.jsp
wget -O _zipcode_DB.zip https://www.epost.go.kr/search/areacd/zipcode_DB.zip
wget -O _areacd_chgaddr_DB.zip http://www.epost.go.kr/search/areacd/areacd_chgaddr_DB.zip
wget -O _areacd_rangeaddr_DB.zip http://www.epost.go.kr/search/areacd/areacd_rangeaddr_DB.zip
wget -O _areacd_pobox_DB.zip http://www.epost.go.kr/search/areacd/areacd_pobox_DB.zip
