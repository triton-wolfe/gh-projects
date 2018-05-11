function moves = solveCube(g0,g1,ts0,ts1)
    global s0 s1 g0possible g1possible
    s0 = ts0;
    s1 = ts1;
    g0possible = {'','U','U''','U2','D','D''','D2','L','L''','L2','R','R''','R2','F','F''','F2','B','B''','B2'};
    g1possible = {'','U','U''','U2','D','D''','D2','L2','R2','F2','B2'};
    
    move1 = g0solve(g0,1)
    for i = strsplit(strtrim(move1))
        g1 = g1turn(g1,i{1});
    end
    move2 = g1solve(g1,1)
    moves = [strtrim(move1) ' ' strtrim(move2)];
    moves = simp(moves);
end

function [moves, issolved] = g0solve(g0,depth)
    global s0 g0possible
    issolved = false;
    moves = '';
    for i = 1:19
        if depth == 18
            g0temp = g0turn(g0,g0possible{i});
            if isequal(g0temp,s0)
                moves = g0possible{i};
                issolved = true;
                return
            end
        else
            g0temp = g0turn(g0,g0possible{i});
            [moves, issolved] = g0solve(g0temp,depth+1);
            if issolved
                moves = [g0possible{i} ' ' moves];
                return
            end
        end
    end
end

function [moves, issolved] = g1solve(g1,depth)
    global s1 g1possible
    issolved = false;
    moves = '';
    for i = 1:11
        if depth == 12
            g1temp = g1turn(g1,g1possible{i});
            if isequal(g1temp,s1)
                moves = g1possible{i};
                issolved = true;
                return
            end
        else
            g1temp = g1turn(g1,g1possible{i});
            [moves, issolved] = g1solve(g1temp,depth+1);
            if issolved
                moves = [g1possible{i} ' ' moves];
                return
            end
        end
    end
end

function moves = simp(moves)
    moves = strsplit(strtrim(moves),' ');
    i = 1;
    while i < length(moves)
        if strcmp(moves{i}(1),moves{i+1}(1))
            if length(moves{i}) == length(moves{i+1}) && length(moves{i}) == 1
                moves{i} = [moves{i} '2'];
                moves(i+1) = [];
            elseif length(moves{i}) == length(moves{i+1})
                moves{i} = moves{i}(1);
                moves(i+1) = [];
            else
                moves{i} = [moves{i} ''''];
                moves(i+1) = [];
            end
        else
            i = i + 1;
        end
    end
    moves = strjoin(moves,' ');
end

function g0 = g0turn(g0,str)
    switch str
        case 'U'
            g0(:,:,7:9) = rot90(g0(:,:,7:9),3);
        case 'U'''
            g0(:,:,7:9) = rot90(g0(:,:,7:9),1);
        case 'U2'
            g0(:,:,7:9) = rot90(g0(:,:,7:9),2);
        case 'D'
            g0(:,:,1:3) = rot90(g0(:,:,1:3),1);
        case 'D'''
            g0(:,:,1:3) = rot90(g0(:,:,1:3),3);
        case 'D2'
            g0(:,:,1:3) = rot90(g0(:,:,1:3),2);
        case 'L'
            g0(1:3,:,:) = permute(rot90(permute(g0(1:3,:,:),[3 2 1]),3),[3 2 1]);
        case 'L'''
            g0(1:3,:,:) = permute(rot90(permute(g0(1:3,:,:),[3 2 1]),1),[3 2 1]);
        case 'L2'
            g0(1:3,:,:) = permute(rot90(permute(g0(1:3,:,:),[3 2 1]),2),[3 2 1]);
        case 'R'
            g0(7:9,:,:) = permute(rot90(permute(g0(7:9,:,:),[3 2 1]),1),[3 2 1]);
        case 'R'''
            g0(7:9,:,:) = permute(rot90(permute(g0(7:9,:,:),[3 2 1]),3),[3 2 1]);
        case 'R2'
            g0(7:9,:,:) = permute(rot90(permute(g0(7:9,:,:),[3 2 1]),2),[3 2 1]);
        case 'F'
            g0(:,1:3,:) = permute(rot90(permute(g0(:,1:3,:),[1 3 2]),3),[1 3 2]);
        case 'F'''
            g0(:,1:3,:) = permute(rot90(permute(g0(:,1:3,:),[1 3 2]),1),[1 3 2]);
        case 'F2'
            g0(:,1:3,:) = permute(rot90(permute(g0(:,1:3,:),[1 3 2]),2),[1 3 2]);
        case 'B'
            g0(:,7:9,:) = permute(rot90(permute(g0(:,7:9,:),[1 3 2]),1),[1 3 2]);
        case 'B'''
            g0(:,7:9,:) = permute(rot90(permute(g0(:,7:9,:),[1 3 2]),3),[1 3 2]);
        case 'B2'
            g0(:,7:9,:) = permute(rot90(permute(g0(:,7:9,:),[1 3 2]),2),[1 3 2]);
    end

end

function g1 = g1turn(g1,str)
    switch str
        case 'U'
            g1(:,:,3) = rot90(g1(:,:,3),3);
        case 'U'''
            g1(:,:,3) = rot90(g1(:,:,3),1);
        case 'U2'
            g1(:,:,3) = rot90(g1(:,:,3),2);
        case 'D'
            g1(:,:,1) = rot90(g1(:,:,1),1);
        case 'D'''
            g1(:,:,1) = rot90(g1(:,:,1),3);
        case 'D2'
            g1(:,:,1) = rot90(g1(:,:,1),2);
        case 'L'
            g1(1,:,:) = permute(rot90(permute(g1(1,:,:),[3 2 1]),3),[3 2 1]);
        case 'L'''
            g1(1,:,:) = permute(rot90(permute(g1(1,:,:),[3 2 1]),1),[3 2 1]);
        case 'L2'
            g1(1,:,:) = permute(rot90(permute(g1(1,:,:),[3 2 1]),2),[3 2 1]);
        case 'R'
            g1(3,:,:) = permute(rot90(permute(g1(3,:,:),[3 2 1]),1),[3 2 1]);
        case 'R''' 
            g1(3,:,:) = permute(rot90(permute(g1(3,:,:),[3 2 1]),3),[3 2 1]);
        case 'R2'
            g1(3,:,:) = permute(rot90(permute(g1(3,:,:),[3 2 1]),2),[3 2 1]);
        case 'F'
            g1(:,1,:) = permute(rot90(permute(g1(:,1,:),[1 3 2]),3),[1 3 2]);
        case 'F'''
            g1(:,1,:) = permute(rot90(permute(g1(:,1,:),[1 3 2]),1),[1 3 2]);
        case 'F2'
            g1(:,1,:) = permute(rot90(permute(g1(:,1,:),[1 3 2]),2),[1 3 2]);
        case 'B'
            g1(:,3,:) = permute(rot90(permute(g1(:,3,:),[1 3 2]),1),[1 3 2]);
        case 'B'''
            g1(:,3,:) = permute(rot90(permute(g1(:,3,:),[1 3 2]),3),[1 3 2]);
        case 'B2'
            g1(:,3,:) = permute(rot90(permute(g1(:,3,:),[1 3 2]),2),[1 3 2]);
    end
end

% function moves = g0solve(prev)
%     global s0
%     str = {'U','U''','U2','D','D''','D2','L','L''','L2','R','R''','R2','F','F''','F2','B','B''','B2'}; %{'U','D','L','R','F','D'};
%     len = length(str);
%     if isequal(prev{1},s0)
%         moves = '';
%         return
%     end
%     while true
%         dimvec = size(prev);
%         new = cell(2,dimvec(2)*len);
%         for i = 1:dimvec(2)
%             chr = prev{1,i};
%             sr = prev{2,i};
%             for j = 1:len
%                 pos = (i-1)*len+j;
%                 tchr = g0turn(chr,str{j});
%                 if isequal(tchr,s0)
%                     moves = [sr ' ' str{j}];
%                     return
%                 else %if ~any(cellfun(@(x) isequal(x,temp{2}),prev(2,:)))
%                     new{1,pos} = tchr;
%                     new{2,pos} = [sr ' ' str{j}];
%                 end
%             end
%         end
%         prev = new;
%     end
% end

% function moves = g1solve(prev)
%     global s1
%     str = {'U','U''','U2','D','D''','D2','L2','R2','F2','B2'}; %{'U','D','L2','R2','F2','D2'};
%     len = length(str);
%     if isequal(prev{1},s1)
%         moves = '';
%         return
%     end
%     while true
%         dimvec = size(prev);
%         new = cell(2,dimvec(2)*len);
%         for i = 1:dimvec(2)
%             dbl = prev{1,i};
%             sr = prev{2,i};
%             for j = 1:len
%                 pos = (i-1)*len+j;
%                 tdbl = g1turn(dbl,str{j});
%                 if isequal(tdbl,s1)
%                     moves = [sr ' ' str{j}];
%                     return
%                 else %if ~any(cellfun(@(x) isequal(x,temp),prev(1,:)))
%                     new{1,pos} = tdbl;
%                     new{2,pos} = [sr ' ' str{j}];
%                 end
%             end
%         end
%         prev = new;
%     end
% end