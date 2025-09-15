-module(erlang_greeter_demo).
-behaviour(gen_server).

-export([init/1, handle_info/2, start/0]).

init(replier) -> {ok, replier};
init(greeter) -> {ok, greeter}.

handle_info({greet, Whom, ReplyPid}, greeter) ->
  io:format("Hello ~s!~n", [Whom]),
  ReplyPid ! {greeted, Whom},
  {noreply, greeter};

handle_info({greeted, Whom}, replier) ->
  io:format("Received greeting from ~s!~n", [Whom]),
  {noreply, replier}.

start() ->
  {ok, ReplierPid} = gen_server:start_link({local, replier}, ?MODULE, replier, []),
  {ok, GreeterPid} = gen_server:start_link({local, greeter}, ?MODULE, greeter, [ReplierPid]),
  GreeterPid ! {greet, "Roger", ReplierPid},
  ok.