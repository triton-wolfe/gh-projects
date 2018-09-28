classdef bond
    properties
        Type char {mustBeMember(Type,{'single','double','triple'})} = 'single';
        Location (2,3) double {mustBeReal, mustBeFinite}
        Connections (1,2) atom
    end
    methods
        function [obj,changed] = setLoc(obj)
            if obj.Connections(1).isRoot || ~isequal(obj.Connections(1).Location,[0 0 0])
                if obj.Connections(1).isRoot
                    obj.Location(1,:) = zeros(1,3);
                end
                
            elseif obj.Connections(2).isRoot || ~isequal(obj.Connections(2).Location,[0 0 0])
                if obj.Connections(2).isRoot
                    obj.Location(2,:) = zeros(1,3);
                end
                
            else
                changed = false;
            end
        end
    end
end