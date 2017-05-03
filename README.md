# Telnet server

## Why would you want to (re) create a server using specs that are over 25 years old?

I wanted to know if I could create a telnet server using the RFC's on Telnet. Guess what: I can.

## Was it easy?

Hell no. I don't have all things figured out as I wanted them but I am happy with the results.

## What can it do?

No much. It was a small experiment. The server will negotiate on terminal type, line mode, echo and naws. Meaning:
 
 * It will know how big your terminal is and if it supports 256 colors or not. 
 * I will echo itself; so each character is send to the server instead of each line (inefficient but fun for games of weird demo stuff).

## Sounds really easy...

Well the specs tell a lot but finding the right order to handle the commands; that was not mentioned in the specs. It 
took some searching on the internet in existing code, stackoverflow and experimenting. So no: not really.

## Did you test it?

Yep. Used putty as client and the (gnu) telnet. They actually send different command to the server. They both implement
the specs but still differ a bit.

# Builing the code

    gradle run

Will build and start the telnet server on port 2300. Type `help` for info.