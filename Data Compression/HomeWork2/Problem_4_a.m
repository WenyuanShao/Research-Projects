x=[];
y=[];

for i = 0:31
    x = [x;i^2/3];
    y = [y;sin((2*i+1)*pi/32)];
end

n = 32;
H = [];
a=1/sqrt(n);
for i=1:n
    H(1,i)=a;
end
for k=1:n-1
    p=fix(log2(k));
    q=k-2^p+1;
    t1=n/2^p;
    sup=fix(q*t1);
    mid=fix(sup-t1/2);
    inf=fix(sup-t1);
    t2=2^(p/2)*a;
    for j=1:inf
        H(k+1,j)=0;
    end
    for j=inf+1:mid
        H(k+1,j)=t2;
    end
    for j=mid+1:sup
        H(k+1,j)=-t2;
    end
    for j=sup+1:n
        H(k+1,j)=0;
    end
end

haar_X = H * x;
haar_Y = H * y;

haar_X_abs = abs(haar_X);
haar_Y_abs = abs(haar_Y);

[sort_X,index_X] = sortrows(haar_X_abs,1);
[sort_Y,index_Y] = sortrows(haar_Y_abs,1);

Xsmallest = index_X(1:17);
Ysmallest = index_Y(1:17);

for i = 1:17
    haar_X(index_X(i)) = 0;
    haar_Y(index_Y(i)) = 0;
end

inv_X = H\haar_X;
inv_Y = H\haar_Y;


figure(1);
plot(inv_X);
hold on;
plot(x);

figure(2);
plot(inv_Y);
hold on;
plot(y);
