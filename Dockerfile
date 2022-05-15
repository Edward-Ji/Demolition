FROM docker.io/archlinux

# update config
RUN lspci -tvv
RUN printf "[multilib]\nInclude = /etc/pacman.d/mirrorlist" >> /etc/pacman.conf
RUN printf "4\nY" > conf

# update packages
RUN pacman -Syu --noconfirm --needed

# install packages
RUN pacman -S neofetch --noconfirm --needed
RUN pacman -S xfce4 --noconfirm --needed
RUN pacman -S xorg-server-xvfb --noconfirm --needed
RUN pacman -S gradle --needed < conf
RUN neofetch

# cleanup config
RUN rm conf

# add files
ADD . /demo/
WORKDIR /demo

# run test
RUN xvfb-run gradle build --stacktrace --info
