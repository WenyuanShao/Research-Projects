function [Y] = UniformTransform2(k,G,D,R)

[MAX, index_max] = max(G(:));
[MIN, index_min] = min(G(:));
delta = (MAX + 0.001 - MIN)/k;
D = [];
D(1) = MIN;

for i = 2:k+1
    D(i) = D(i-1) + delta;
end

[n,QG] = histc(G,D);
QG = QG - 1;

R = [];
for i = 1:k
    R(i) = (D(i) + D(i+1))/2;
end

DQG = [];
[row,column] = size(QG);
for i = 1:row
    for j = 1:column
        DQG(i,j) = R(QG(i,j)+1);
    end
end

Y = DQG;