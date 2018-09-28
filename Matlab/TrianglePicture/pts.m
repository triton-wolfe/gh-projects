points = [1 .8;0 0];
rot120 = [cosd(120) -sind(120);sind(120) cosd(120)];
points = [points rot120*points(:,2) rot120*points(:,1)];
h1 = patch(points(1,:),points(2,:),'k');
h2 = patch(points(1,:),points(2,:),'k');
h3 = patch(points(1,:),points(2,:),'k');
rotate(h1,[0 0 1],120,[0 0 0])
rotate([h1,h2],[0 0 1],120,[0 0 0])
