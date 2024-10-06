# Minecraft Of Electron
Currently only supports Windows
## How to achieve it
Use Dwm to get the window handles of Minecraft and Electron, and then adjust the rendering level on the Windows window so that they overlap. You can understand it as imGui.
## In the future
I just simply ported a demo, and I need some ideas to build other features, such as rendering in Minecraft, to replace the original GUI system, which will take a long time
## Other
In the past three years, I have been building for this idea, but after trying for a long time, I want to give up on this project because using Windows API does not support too many things. I started considering using a browser to forward textures through SOCKET for Minecraft to render, which may take some time.

Of course, this is much more performance consuming than using Windows APIs, and it won't be too long