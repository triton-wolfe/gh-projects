function moves = moveGen(g0)
    global s0 possible
    blank = char(' '*ones(3,3,3));
    bot = blank;
    bot(2,2,1) = '+';
    top = blank;
    top(2,2,3) = '+';
    frt = blank;
    frt(1,2,2) = '-';
    bck = blank;
    bck(3,2,2) = '-';
    l1 = [bot bot bot;bot blank bot; bot bot bot];
    l2 = [frt blank frt;blank blank blank;bck blank bck];
    l3 = [top top top;top blank top; top top top];
    s0 = cat(3,l1,l2,l3);
    possible = {'','U','U''','U2','D','D''','D2','L','L''','L2','R','R''','R2','F','F''','F2','B','B''','B2'};
    moves = g0solve(g0,'',6);
end

function [moves issolved] = g0solve(g0,depth)
    global s0 possible
    issolved = false;
    for i = 1:19
        if depth == 18
            g0temp = g0turn(g0,possible{i});
            if isequal(g0temp,s0)
                moves = possible{i};
                issolved = true;
                return
            end
        else
            [moves issolved] = g0solve(g0temp,depth+1);
            if issolved
                moves = [possible{i} ' ' moves];
                return
            end
        end
    end
end