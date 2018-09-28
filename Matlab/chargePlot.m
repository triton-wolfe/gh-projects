function chargePlot(matrix,ax)
    [x y] = meshgrid(linspace(ax(1),ax(2)),linspace(ax(3),ax(4)));
    [r c] = size(x);
    [l junk] = size(matrix);
    z = zeros(r,c);
    for i = 1:r
        for j = 1:c
            for k = 1:l
                dist = sqrt((x(i,j)-matrix(k,2))^2+(y(i,j)-matrix(k,3))^2);
                z(i,j) = z(i,j) + matrix(k,1)*4/dist;
            end
        end
    end
    hold on
    view(3);
    surf(x,y,z);
    hold off
    axis([ax(1),ax(2),ax(3),ax(4),min(min(z))/2,max(max(z))])
        %max(max(z)/2)])
end