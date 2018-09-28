function crazyGraphs(type,a,b,c,r)
    [x y] = meshgrid(linspace(-1,1),linspace(-1,1));
    [rows cols] = size(x);
    switch type
        case 'Ellipsoid'
            tempposz = sqrt((c.^2).*(r - ((x.^2)./(a.^2)) - ((y.^2)./(b.^2))));
            tempnegz = -sqrt((c.^2).*(r - ((x.^2)./(a.^2)) - ((y.^2)./(b.^2))));
        case 'Elliptic Paraboloid'
            tempposz = c.*(((x.^2)./(a.^2)) + ((y.^2)./(b.^2)));
            tempnegz = c.*(((x.^2)./(a.^2)) + ((y.^2)./(b.^2)));
        case 'Hyperbolic Paraboloid'
            tempposz = c.*(((x.^2)./(a.^2)) - ((y.^2)./(b.^2)));
            tempnegz = c.*(((x.^2)./(a.^2)) - ((y.^2)./(b.^2)));
        case 'Cone'
            tempposz = sqrt((c.^2).*((x.^2)./(a.^2)) + ((y.^2)./(b.^2)));
            tempnegz = -sqrt((c.^2).*((x.^2)./(a.^2)) + ((y.^2)./(b.^2)));
        case 'Hyperboloid of One Sheet'
            tempposz = sqrt((c.^2).*(((x.^2)./(a.^2)) + ((y.^2)./(b.^2)) - r));
            tempnegz = -sqrt((c.^2).*(((x.^2)./(a.^2)) + ((y.^2)./(b.^2)) - r));
        case 'Hyperboloid of Two Sheets'
            tempposz = sqrt((c.^2).*(r + ((x.^2)./(a.^2)) + ((y.^2)./(b.^2))));
            tempnegz = -sqrt((c.^2).*(r + ((x.^2)./(a.^2)) + ((y.^2)./(b.^2))));
        otherwise
            'ERROR NOT A TYPE'
    end
    
    
    posz = zeros(rows,cols);
    negz = zeros(rows,cols);
    for i = 1:rows
        for j = 1:cols
            if isreal(tempposz(i,j))
                posz(i,j) = tempposz(i,j);
                negz(i,j) = tempnegz(i,j);
            else
                a1 = min(i+1,rows);
                a2 = min(j+1,cols);
                a3 = max(i-1,1);
                a4 = max(j-1,1);
                if ~any([isreal(tempposz(a1,j)) isreal(tempposz(i,a2)) isreal(tempposz(a3,j)) isreal(tempposz(i,a4))])
                    posz(i,j) = 0;
                    negz(i,j) = 0;
                    x(i,j) = 0;
                    y(i,j) = 0;
                 end
             end
        end
    end
    hold on
    view(3);
    surf(x,y,posz);
    surf(x,y,negz);
    hold off
    axis square
end