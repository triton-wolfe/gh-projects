function rubiks(scramble,varargin)
    global h g0 g1 s0 s1 speed
    close all
    figure('Position',[1.8 41.8 766.4 740.8])
    h = cell(3,3,3);
    [h,g0,g1,s0,s1] = setup(h);
        
    if ~exist('scramble','var')
        scramble = '';
    end
    if any(strcmp(varargin(1:2:end),'Speed'))
        var = strcmp(varargin(1:end),'Speed');
        var = [false var(1:end-1)];
        var = varargin{var};
        if isnumeric(var) && mod(90,var) == 0
            speed = var;
        elseif isnumeric(var)
            possible = [1 2 3 5 6 9 10 15 18 30 45 90];
            [~,ind] = min(abs(var-possible));
            speed = possible(ind);
        elseif ischar(var)
            switch lower(var)
                case 'vvfast'
                    speed = 30;
                case 'vfast'
                    speed = 18;
                case 'fast'
                    speed = 9;
                case 'medium'
                    speed = 3;
                case 'slow'
                    speed = 1;
                otherwise
                    speed = 3;
            end
        else
            speed = 3;
        end
    else
        speed = 3;
    end
    for i =  strsplit(strtrim(scramble),' ')
        turn(i{1},false)
    end
    go = true;
    while go
        str = input('Turns: ','s');
        for i =  strsplit(str,' ')
            if contains(str,{'close','exit','end'},'IgnoreCase',true)
                go = ~go;
            elseif contains(str,'reset','IgnoreCase',true)
                h{2,2,2} = [];
                cellfun(@delete,h)
                [h,g0,g1,s0,s1] = setup(h);
            elseif contains(str,'solve','IgnoreCase',true)
                tic
                moves = solveCube(g0,g1,s0,s1)
                toc
                for j = strsplit(moves,' ')
                    turn(j{1},true)
                end
            end
            turn(i{1},true)
        end
    end
    close all
end

function turn(ui,animate)
    global h g0 g1 s0 s1 speed
    if ischar(ui)
        var = ui;
    else 
        var = h{2,2,2}{ui.Parent.Children(2).Value};
    end
    if ~islogical(animate)
        animate = true;
    end
    switch upper(var)
        case 'U'
            temp = h(:,:,3);
            rot(temp,[0 0 1],-speed,1,animate)
            h(:,:,3) = rot90(temp,3);
            g0(:,:,7:9) = rot90(g0(:,:,7:9),3);
            g1(:,:,3) = rot90(g1(:,:,3),3);
        case 'U'''
            temp = h(:,:,3);
            rot(temp,[0 0 1],speed,1,animate)
            h(:,:,3) = rot90(temp,1);
            g0(:,:,7:9) = rot90(g0(:,:,7:9),1);
            g1(:,:,3) = rot90(g1(:,:,3),1);
        case 'U2'
            temp = h(:,:,3);
            rot(temp,[0 0 1],-speed,2,animate)
            h(:,:,3) = rot90(temp,2);
            g0(:,:,7:9) = rot90(g0(:,:,7:9),2);
            g1(:,:,3) = rot90(g1(:,:,3),2);
        case 'D'
            temp = h(:,:,1);
            rot(temp,[0 0 -1],-speed,1,animate)
            h(:,:,1) = rot90(temp,1);
            g0(:,:,1:3) = rot90(g0(:,:,1:3),1);
            g1(:,:,1) = rot90(g1(:,:,1),1);
        case 'D'''
            temp = h(:,:,1);
            rot(temp,[0 0 -1],speed,1,animate)
            h(:,:,1) = rot90(temp,3);
            g0(:,:,1:3) = rot90(g0(:,:,1:3),3);
            g1(:,:,1) = rot90(g1(:,:,1),3);
        case 'D2'
            temp = h(:,:,1);
            rot(temp,[0 0 -1],-speed,2,animate)
            h(:,:,1) = rot90(temp,2);
            g0(:,:,1:3) = rot90(g0(:,:,1:3),2);
            g1(:,:,1) = rot90(g1(:,:,1),2);
        case 'L'
            temp = h(1,:,:);
            rot(temp,[1 0 0],speed,1,animate)
            h(1,:,:) = permute(rot90(permute(temp,[3 2 1]),3),[3 2 1]);
            g0(1:3,:,:) = permute(rot90(permute(g0(1:3,:,:),[3 2 1]),3),[3 2 1]);
            g1(1,:,:) = permute(rot90(permute(g1(1,:,:),[3 2 1]),3),[3 2 1]);
        case 'L''' 
            temp = h(1,:,:);
            rot(temp,[1 0 0],-speed,1,animate)
            h(1,:,:) = permute(rot90(permute(temp,[3 2 1]),1),[3 2 1]);
            g0(1:3,:,:) = permute(rot90(permute(g0(1:3,:,:),[3 2 1]),1),[3 2 1]);
            g1(1,:,:) = permute(rot90(permute(g1(1,:,:),[3 2 1]),1),[3 2 1]);
        case 'L2' 
            temp = h(1,:,:);
            rot(temp,[1 0 0],speed,2,animate)
            h(1,:,:) = permute(rot90(permute(temp,[3 2 1]),2),[3 2 1]);
            g0(1:3,:,:) = permute(rot90(permute(g0(1:3,:,:),[3 2 1]),2),[3 2 1]);
            g1(1,:,:) = permute(rot90(permute(g1(1,:,:),[3 2 1]),2),[3 2 1]);
        case 'R'
            temp = h(3,:,:);
            rot(temp,[-1 0 0],speed,1,animate)
            h(3,:,:) = permute(rot90(permute(temp,[3 2 1]),1),[3 2 1]);
            g0(7:9,:,:) = permute(rot90(permute(g0(7:9,:,:),[3 2 1]),1),[3 2 1]);
            g1(3,:,:) = permute(rot90(permute(g1(3,:,:),[3 2 1]),1),[3 2 1]);
        case 'R''' 
            temp = h(3,:,:);
            rot(temp,[-1 0 0],-speed,1,animate)
            h(3,:,:) = permute(rot90(permute(temp,[3 2 1]),3),[3 2 1]);
            g0(7:9,:,:) = permute(rot90(permute(g0(7:9,:,:),[3 2 1]),3),[3 2 1]);
            g1(3,:,:) = permute(rot90(permute(g1(3,:,:),[3 2 1]),3),[3 2 1]);
        case 'R2'
            temp = h(3,:,:);
            rot(temp,[-1 0 0],speed,2,animate)
            h(3,:,:) = permute(rot90(permute(temp,[3 2 1]),2),[3 2 1]);
            g0(7:9,:,:) = permute(rot90(permute(g0(7:9,:,:),[3 2 1]),2),[3 2 1]);
            g1(3,:,:) = permute(rot90(permute(g1(3,:,:),[3 2 1]),2),[3 2 1]);
        case 'F'
            temp = h(:,1,:);
            rot(temp,[0 1 0],speed,1,animate)
            h(:,1,:) = permute(rot90(permute(temp,[1 3 2]),3),[1 3 2]);
            g0(:,1:3,:) = permute(rot90(permute(g0(:,1:3,:),[1 3 2]),3),[1 3 2]);
            g1(:,1,:) = permute(rot90(permute(g1(:,1,:),[1 3 2]),3),[1 3 2]);
        case 'F'''
            temp = h(:,1,:);
            rot(temp,[0 1 0],-speed,1,animate)
            h(:,1,:) = permute(rot90(permute(temp,[1 3 2]),1),[1 3 2]);
            g0(:,1:3,:) = permute(rot90(permute(g0(:,1:3,:),[1 3 2]),1),[1 3 2]);
            g1(:,1,:) = permute(rot90(permute(g1(:,1,:),[1 3 2]),1),[1 3 2]);
        case 'F2'
            temp = h(:,1,:);
            rot(temp,[0 1 0],speed,2,animate)
            h(:,1,:) = permute(rot90(permute(temp,[1 3 2]),2),[1 3 2]);
            g0(:,1:3,:) = permute(rot90(permute(g0(:,1:3,:),[1 3 2]),2),[1 3 2]);
            g1(:,1,:) = permute(rot90(permute(g1(:,1,:),[1 3 2]),2),[1 3 2]);
        case 'B'
            temp = h(:,3,:);
            rot(temp,[0 -1 0],speed,1,animate)
            h(:,3,:) = permute(rot90(permute(temp,[1 3 2]),1),[1 3 2]);
            g0(:,7:9,:) = permute(rot90(permute(g0(:,7:9,:),[1 3 2]),1),[1 3 2]);
            g1(:,3,:) = permute(rot90(permute(g1(:,3,:),[1 3 2]),1),[1 3 2]);
        case 'B'''
            temp = h(:,3,:);
            rot(temp,[0 -1 0],-speed,1,animate)
            h(:,3,:) = permute(rot90(permute(temp,[1 3 2]),3),[1 3 2]);
            g0(:,7:9,:) = permute(rot90(permute(g0(:,7:9,:),[1 3 2]),3),[1 3 2]);
            g1(:,3,:) = permute(rot90(permute(g1(:,3,:),[1 3 2]),3),[1 3 2]);
        case 'B2'
            temp = h(:,3,:);
            rot(temp,[0 -1 0],speed,2,animate)
            h(:,3,:) = permute(rot90(permute(temp,[1 3 2]),2),[1 3 2]);
            g0(:,7:9,:) = permute(rot90(permute(g0(:,7:9,:),[1 3 2]),2),[1 3 2]);
            g1(:,3,:) = permute(rot90(permute(g1(:,3,:),[1 3 2]),2),[1 3 2]);
        case 'X'
            temp = h;
            temp{2,2,2} = [];
            rot(temp,[-1 0 0],speed,1,animate)
            h = permute(rot90(permute(h,[3 2 1]),1),[3 2 1]);
            g0 = permute(rot90(permute(g0,[3 2 1]),1),[3 2 1]);
            g1 = permute(rot90(permute(g1,[3 2 1]),1),[3 2 1]);
            s0 = permute(rot90(permute(s0,[3 2 1]),1),[3 2 1]);
            s1 = permute(rot90(permute(s1,[3 2 1]),1),[3 2 1]);
        case 'X'''
            temp = h;
            temp{2,2,2} = [];
            rot(temp,[-1 0 0],-speed,1,animate)
            h = permute(rot90(permute(h,[3 2 1]),3),[3 2 1]);
            g0 = permute(rot90(permute(g0,[3 2 1]),3),[3 2 1]);
            g1 = permute(rot90(permute(g1,[3 2 1]),3),[3 2 1]);
            s0 = permute(rot90(permute(s0,[3 2 1]),3),[3 2 1]);
            s1 = permute(rot90(permute(s1,[3 2 1]),3),[3 2 1]);
        case 'X2'
            temp = h;
            temp{2,2,2} = [];
            rot(temp,[-1 0 0],speed,2,animate)
            h = permute(rot90(permute(h,[3 2 1]),2),[3 2 1]);
            g0 = permute(rot90(permute(g0,[3 2 1]),2),[3 2 1]);
            g1 = permute(rot90(permute(g1,[3 2 1]),2),[3 2 1]);
            s0 = permute(rot90(permute(s0,[3 2 1]),2),[3 2 1]);
            s1 = permute(rot90(permute(s1,[3 2 1]),2),[3 2 1]);
        case 'Y'
            temp = h;
            temp{2,2,2} = [];
            rot(temp,[0 0 -1],speed,1,animate)
            h = rot90(h,3);
            g0 = rot90(g0,3);
            g1 = rot90(g1,3);
            s0 = rot90(s0,3);
            s1 = rot90(s1,3);
        case 'Y'''
            temp = h;
            temp{2,2,2} = [];
            rot(temp,[0 0 -1],-speed,1,animate)
            h = rot90(h,1);
            g0 = rot90(g0,1);
            g1 = rot90(g1,1);
            s0 = rot90(s0,1);
            s1 = rot90(s1,1);
        case 'Y2'
            temp = h;
            temp{2,2,2} = [];
            rot(temp,[0 0 -1],speed,2,animate)
            h = rot90(h,2);
            g0 = rot90(g0,2);
            g1 = rot90(g1,2);
            s0 = rot90(s0,2);
            s1 = rot90(s1,2);
        case 'Z'
            temp = h;
            temp{2,2,2} = [];
            rot(temp,[0 1 0],speed,1,animate)
            h = permute(rot90(permute(h,[1 3 2]),3),[1 3 2]);
            g0 = permute(rot90(permute(g0,[1 3 2]),3),[1 3 2]);
            g1 = permute(rot90(permute(g1,[1 3 2]),3),[1 3 2]);
            s0 = permute(rot90(permute(s0,[1 3 2]),3),[1 3 2]);
            s1 = permute(rot90(permute(s1,[1 3 2]),3),[1 3 2]);
        case 'Z'''
            temp = h;
            temp{2,2,2} = [];
            rot(temp,[0 1 0],-speed,1,animate)
            h = permute(rot90(permute(h,[1 3 2]),1),[1 3 2]);
            g0 = permute(rot90(permute(g0,[1 3 2]),1),[1 3 2]);
            g1 = permute(rot90(permute(g1,[1 3 2]),1),[1 3 2]);
            s0 = permute(rot90(permute(s0,[1 3 2]),1),[1 3 2]);
            s1 = permute(rot90(permute(s1,[1 3 2]),1),[1 3 2]);
        case 'Z2'
            temp = h;
            temp{2,2,2} = [];
            rot(temp,[0 1 0],speed,2,animate)
            h = permute(rot90(permute(h,[1 3 2]),2),[1 3 2]);
            g0 = permute(rot90(permute(g0,[1 3 2]),2),[1 3 2]);
            g1 = permute(rot90(permute(g1,[1 3 2]),2),[1 3 2]);
            s0 = permute(rot90(permute(s0,[1 3 2]),2),[1 3 2]);
            s1 = permute(rot90(permute(s1,[1 3 2]),2),[1 3 2]);
        case {'','SOLVE','CLOSE','EXIT','END','RESET'}
        otherwise
            fprintf('Command "%s" Not Recognized\n',var)
    end
end

function rot(h,direction,deg,th,animate)
    if ~exist('animate','var')
        animate = false;
    end
    if animate
        for t = 1:(th*90/abs(deg))
            cellfun(@(x) rotate(x,direction,deg,[0 0 0]),h)
            pause(.001)
        end
    else
        cellfun(@(x) rotate(x,direction,90,[0 0 0]),h)
    end
end

function [h,g0,g1,s0,s1] = setup(h)
    w = [1 1 1]; y = [1 1 0]; g = [0 1 0]; b = [0 0 1]; r = [1 0 0]; o = [1 .5 0];
    
    h{2,2,2} = {'U','U''','U2','D','D''','D2','L','L''','L2','R','R''','R2','F','F''','F2','B','B''','B2','x','x2','x''','y','y2','y''','z','z2','z'''};
    
    h{3,3,3} = cor([w;r;b]); shift(h{3,3,3},[-1.5 -1.5 -1.5]); rotate(h{3,3,3},[1 1 0],180,[0 0 0]); rotate(h{3,3,3},[0 0 1],180,[0 0 0]);
    h{1,1,3} = cor([w;o;g]); shift(h{1,1,3},[-1.5 -1.5 -1.5]); rotate(h{1,1,3},[1 1 0],180,[0 0 0]);
    h{3,1,3} = cor([w;g;r]); shift(h{3,1,3},[-1.5 -1.5 -1.5]); rotate(h{3,1,3},[1 1 0],180,[0 0 0]); rotate(h{3,1,3},[0 0 1],90,[0 0 0]);
    h{1,3,3} = cor([w;b;o]); shift(h{1,3,3},[-1.5 -1.5 -1.5]); rotate(h{1,3,3},[1 1 0],180,[0 0 0]); rotate(h{1,3,3},[0 0 1],270,[0 0 0]);
    h{1,1,1} = cor([y;g;o]); shift(h{1,1,1},[-1.5 -1.5 -1.5]);
    h{1,3,1} = cor([y;o;b]); shift(h{1,3,1},[-1.5 -1.5 -1.5]); rotate(h{1,3,1},[0 0 1],270,[0 0 0]);
    h{3,3,1} = cor([y;b;r]); shift(h{3,3,1},[-1.5 -1.5 -1.5]); rotate(h{3,3,1},[0 0 1],180,[0 0 0]);
    h{3,1,1} = cor([y;r;g]); shift(h{3,1,1},[-1.5 -1.5 -1.5]); rotate(h{3,1,1},[0 0 1],90,[0 0 0]);
    
    h{2,1,1} = edg([y;g]); shift(h{2,1,1},[-.5 -1.5 -1.5]);
    h{1,2,1} = edg([y;o]); shift(h{1,2,1},[-.5 -1.5 -1.5]); rotate(h{1,2,1},[0 0 1],270,[0 0 0]);
    h{2,3,1} = edg([y;b]); shift(h{2,3,1},[-.5 -1.5 -1.5]); rotate(h{2,3,1},[0 0 1],180,[0 0 0]);
    h{3,2,1} = edg([y;r]); shift(h{3,2,1},[-.5 -1.5 -1.5]); rotate(h{3,2,1},[0 0 1],90,[0 0 0]);
    h{2,1,3} = edg([w;g]); shift(h{2,1,3},[-.5 -1.5 -1.5]); rotate(h{2,1,3},[0 1 0],180,[0 0 0]);
    h{1,2,3} = edg([w;o]); shift(h{1,2,3},[-.5 -1.5 -1.5]); rotate(h{1,2,3},[0 1 0],180,[0 0 0]); rotate(h{1,2,3},[0 0 1],270,[0 0 0]);
    h{2,3,3} = edg([w;b]); shift(h{2,3,3},[-.5 -1.5 -1.5]); rotate(h{2,3,3},[0 1 0],180,[0 0 0]); rotate(h{2,3,3},[0 0 1],180,[0 0 0]);
    h{3,2,3} = edg([w;r]); shift(h{3,2,3},[-.5 -1.5 -1.5]); rotate(h{3,2,3},[0 1 0],180,[0 0 0]); rotate(h{3,2,3},[0 0 1],90,[0 0 0]);
    h{1,1,2} = edg([o;g]); shift(h{1,1,2},[-.5 -1.5 -1.5]); rotate(h{1,1,2},[0 1 0],90,[0 0 0]);
    h{3,1,2} = edg([r;g]); shift(h{3,1,2},[-.5 -1.5 -1.5]); rotate(h{3,1,2},[0 1 0],270,[0 0 0]);
    h{1,3,2} = edg([b;o]); shift(h{1,3,2},[-.5 -1.5 -1.5]); rotate(h{1,3,2},[0 1 0],90,[0 0 0]); rotate(h{1,3,2},[0 0 1],270,[0 0 0]);
    h{3,3,2} = edg([r;b]); shift(h{3,3,2},[-.5 -1.5 -1.5]); rotate(h{3,3,2},[0 1 0],90,[0 0 0]); rotate(h{3,3,2},[0 0 1],180,[0 0 0]);
    
    h{2,2,1} = center(y); shift(h{2,2,1},[-.5,-.5,-1.5]);
    h{2,3,2} = center(b); shift(h{2,3,2},[-.5,-.5,-1.5]); rotate(h{2,3,2},[1 0 0],90,[0 0 0]);
    h{2,2,3} = center(w); shift(h{2,2,3},[-.5,-.5,-1.5]); rotate(h{2,2,3},[1 0 0],180,[0 0 0]);
    h{2,1,2} = center(g); shift(h{2,1,2},[-.5,-.5,-1.5]); rotate(h{2,1,2},[1 0 0],270,[0 0 0]);
    h{1,2,2} = center(o); shift(h{1,2,2},[-.5,-.5,-1.5]); rotate(h{1,2,2},[0 1 0],90,[0 0 0]);
    h{3,2,2} = center(r); shift(h{3,2,2},[-.5,-.5,-1.5]); rotate(h{3,2,2},[0 1 0],270,[0 0 0]);
    axis square
    axis([-2.122 2.122 -2.122 2.122 -2.122 2.122])
    axis off
    view(3)
 
    uicontrol('Style','popup',...
              'Units','Normalized',...
              'Position',[.01 .01 .18 .08],...
              'String',h{2,2,2},...
              'FontSize',30)
    uicontrol('Style','pushbutton',...
               'Units','Normalized',...
               'Position',[.21 .02 .17 .08],...
               'String','Turn',...
               'Fontsize',30,...
               'Callback',@turn)
	
    blank = zeros(3,3,3);
    bot = blank;
    bot(2,2,1) = '2';
    top = blank;
    top(2,2,3) = '2';
    frt = blank;
    frt(1,2,2) = '1';
    bck = blank;
    bck(3,2,2) = '1';
    l1 = [bot bot bot;bot blank bot; bot bot bot];
    l2 = [frt blank frt;blank blank blank;bck blank bck];
    l3 = [top top top;top blank top; top top top];
    g0 = uint8(cat(3,l1,l2,l3));
    s0 = g0;
    g1 = uint8(reshape(1:27,[3 3 3]));
    s1 = g1;
end

function shift(h,vec)
    dimvec = size(h);
    for r = 1:dimvec(1)
        for c = 1:dimvec(2)
            h(r,c).Vertices = h(r,c).Vertices + vec;
        end
    end
end

function h = cor(c)
    x1 = patch([0 1 1 0],[0 0 1 1],[0 0 0 0],c(1,:));
    y1 = patch([1 1 1],[0 1 1],[0 0 1],c(1,:)); 
    z1 = patch([0 1 1],[1 1 1],[0 0 1],c(1,:));
    x2 = patch([0 1 1 0],[0 0 0 0],[0 0 1 1],c(2,:));
    y2 = patch([1 1 1],[0 0 1],[0 1 1],c(2,:)); 
    z2 = patch([0 1 1],[0 0 1],[1 1 1],c(2,:));
    x3 = patch([0 0 0 0],[0 1 1 0],[0 0 1 1],c(3,:));
    y3 = patch([0 0 1],[1 1 1],[0 1 1],c(3,:)); 
    z3 = patch([0 0 1],[0 1 1],[1 1 1],c(3,:));
    h = [x1 y1 z1 x2 y2 z2 x3 y3 z3];
end

function h = edg(c)
    w1 = patch([0 1 1 0],[0 0 1 1],[0 0 0 0],c(1,:));
    x1 = patch([1 1 1],[0 1 1],[0 0 1],c(1,:)); 
    y1 = patch([0 0 0],[0 1 1],[0 0 1],c(1,:));
    z1 = patch([0 0 1 1],[1 1 1 1],[0 1 1 0],c(1,:));
    w2 = patch([0 1 1 0],[0 0 0 0],[0 0 1 1],c(2,:));
    x2 = patch([1 1 1],[0 0 1],[0 1 1],c(2,:)); 
    y2 = patch([0 0 0],[0 0 1],[0 1 1],c(2,:));
    z2 = patch([0 0 1 1],[0 1 1 0],[1 1 1 1],c(2,:));
    h = [w1 x1 y1 z1 w2 x2 y2 z2];
end

function h = center(c)
    f1 = patch([0 1 1 0],[0 0 1 1],[0 0 0 0],c);
    s1 = patch([0 1 1 0],[0 0 0 0],[0 0 1 1],c);
    s2 = patch([0 1 1 0],[1 1 1 1],[0 0 1 1],c);
    s3 = patch([0 0 0 0],[0 1 1 0],[0 0 1 1],c);
    s4 = patch([1 1 1 1],[0 1 1 0],[0 0 1 1],c);
    h = [f1 s1 s2 s3 s4];
end