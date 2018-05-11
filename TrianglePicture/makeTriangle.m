function makeTriangle
    close all
    fig = figure('Units','normalized','Position',[0 .0463 1 .8667]);
    n = 500;
    dv = [16 9];
    hs = cell(1,n);
    rose = [122 16 40]./255;
    for i = 1:n
        size = rand+.5;
        shiftvec = [(2*rand-1)*((dv(1)/2)-size) (2*rand-1)*((dv(2)/2)-size) 0];
        hs{i} = trngle(size);
        rotate(hs{i},[0 0 1],360*rand,[0 0 0])
        shift(hs{i},shiftvec)
    end
    axis equal
    axis([-8 8 -4.5 4.5])
    axis off
    fig.Color = [0 0 0];
    h = trngle(dv(2)/1.7,rose);
    rotate(h,[0 0 1],90,[0 0 0])
    shift(h,[0 -1.2 0])
    text(-1.8,-1.3,'T','FontSize',300,'Color',rose,'FontWeight','bold')
end

function h = trngle(size,color)
    if ~exist('size','var')
        size = 1;
    end
    if ~exist('color','var')
        temp = .8*rand+.2;
        color = [temp,temp,temp];
        more = {};
    else
        more = {'LineStyle','none'};
    end
    points = [size .7*size;0 0];
    rot120 = [cosd(120) -sind(120);sind(120) cosd(120)];
    points = [points rot120*points(:,2) rot120*points(:,1)];
    h1 = patch(points(1,:),points(2,:),color,more{:});
    h2 = patch(points(1,:),points(2,:),color,more{:});
    h3 = patch(points(1,:),points(2,:),color,more{:});
    rotate(h1,[0 0 1],120,[0 0 0])
    rotate([h1,h2],[0 0 1],120,[0 0 0])
    h = [h1 h2 h3];
end

function shift(h,vec)
    dimvec = size(h);
    for r = 1:dimvec(1)
        for c = 1:dimvec(2)
            h(r,c).Vertices = h(r,c).Vertices + vec;
        end
    end
end