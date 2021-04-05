FROM postgres:13.2
RUN localedef -i de_DE -c -f UTF-8 -A /usr/share/locale/locale.alias en_US.UTF-8
ENV LANG en_US.utf8
COPY initDatabase.sql /docker-entrypoint-initdb.d/