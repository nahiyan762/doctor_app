# Doctor 24x7 - Doctor App (MVVM)


## ROOM
understanding SSOT (Single Source of Truth) concept and retrieving data accordingly
https://medium.com/@iammert/offline-app-with-rxjava-2-and-room-ccd0b5c18101

## SECURING ROOM DB
Using SQLCipher to secure ROOM DB
https://commonsware.com/AndroidArch/previews/securing-your-room

## Generating Hashcode for SMS Retriever API
1) /Applications/Android\ Studio.app/Contents/jre/jdk/Contents/Home/bin/keytool -exportcert -keystore debug.keystore -alias android | xxd -p | tr -d "[:space:]"
2) /Applications/Android\ Studio.app/Contents/jre/jdk/Contents/Home/bin/keytool -exportcert -alias android -keystore /Users/rahulthakur/.android/debug.keystore | xxd -p | tr -d "[:space:]" | echo -n com.traktion.doctor `cat` | shasum -a 256 | tr -d "[:space:]-" | xxd -r -p | base64 | cut -c1-11
Current Debug key - 4G07mFb/860

## Force Updates
Only update with even version codes must be release for normal updates, it version code is odd then it will be a forced update