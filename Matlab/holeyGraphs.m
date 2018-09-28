function holeyGraphs(pt,radius)
    [x y] = meshgrid(linspace(-1,1),linspace(-1,1));
    z = 0.*x + 0.*y + 1;
    circlex = radius.*cos(linspace(0,2.*pi))+pt(1)
    circley = radius.*sin(linspace(0,2.*pi))+pt(2)
    
    
    
    hold on
    view(3);
    surf(x,y,z);
    plot3(circlex,circley,ones(length(circlex)),'m')
    hold off
end