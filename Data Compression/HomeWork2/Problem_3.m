x=[];
y=[];

for i = 0:31
    x = [x;i^2/3];
    y = [y;sin((2*i+1)*pi/32)]
end

X = dct(x);
Y = dct(y);

for i = 16:32
    X(i) = 0;
    Y(i) = 0;
end

i_X = idct(X);
i_Y = idct(Y);

figure(1);
plot(i_X);
hold on;
plot(x);

figure(2);
plot(i_Y);
hold on;
plot(y);







