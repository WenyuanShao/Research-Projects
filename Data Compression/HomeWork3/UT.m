function Y = UT(k,G)
temp = [G(1,2),G(2,1)];
[temp,QG] = UniformTransform(k,temp);
G(1,2) = temp(1);
G(2,1) = temp(2);
% G(1,3) = temp(3);
% G(2,2) = temp(4);
% G(3,1) = temp(5);
% G(1,4) = temp(6);
% G(2,3) = temp(7);
% G(3,2) = temp(8);
% G(4,1) = temp(9);

Y = G;


% G(1,2),G(2,1),G(1,3),G(2,2),G(3,1),G(1,4),G(2,3),G(3,2),G(4,1)