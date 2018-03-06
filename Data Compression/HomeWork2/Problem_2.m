X=[];
Y=[];

for i = 0:31
    X = [X;i^2/3];
    Y = [Y;sin((2*i+1)*pi/32)]
end

fX = fft(X);
fY = fft(Y);
fft_X = fft(X);
fft_Y = fft(Y);
abs_X = abs(fft_X);
abs_Y = abs(fft_Y);


[sort_X,index_X] = sortrows(abs_X,1);
[sort_Y,index_Y] = sortrows(abs_Y,1);

Xsmallest = index_X(1:17);
Ysmallest = index_Y(1:17);

for i = 1:17
    fft_X(index_X(i)) = 0;
    fft_Y(index_Y(i)) = 0;
end

ifft_X = ifft(fft_X);
ifft_Y = ifft(fft_Y);

figure(1);
plot(ifft_X);
hold on;
plot(X);

figure(2);
plot(ifft_Y);
hold on;
plot(Y);




