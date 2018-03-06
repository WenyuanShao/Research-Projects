x=[];
y=[];

for i = 0:31
    x = [x;i^2/3];
    y = [y;sin((2*i+1)*pi/32)];
end

Had = hadamard(32);

Had_X = Had * x;
Had_Y = Had * y;

Had_X_abs = abs(Had_X);
Had_Y_abs = abs(Had_Y);

[sort_X,index_X] = sortrows(Had_X_abs,1);
[sort_Y,index_Y] = sortrows(Had_Y_abs,1);

Xsmallest = index_X(1:17);
Ysmallest = index_Y(1:17);

for i = 1:17
    Had_X(index_X(i)) = 0;
    Had_Y(index_Y(i)) = 0;
end

Had_x = Had\Had_X;
Had_y = Had\Had_Y;

figure(1);
plot(Had_x);
hold on;
plot(x);

figure(2);
plot(Had_y);
hold on;
plot(y);
