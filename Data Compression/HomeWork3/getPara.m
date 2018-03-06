function [D,R] = getPara(k,G)

[MAX, index_max] = max(G(:));
[MIN, index_min] = min(G(:));
delta = (MAX + 0.001 - MIN)/k;
D = [];
D(1) = MIN;

for i = 2:k+1
    D(i) = D(i-1) + delta;
end

R = [];
for i = 1:k
    R(i) = (D(i) + D(i+1))/2;
end



